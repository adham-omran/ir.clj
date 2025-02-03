(ns com.adham-omran.db
  "Utilities to insert to and read from db."
  (:require
   [clojure.java.browse :as browse]
   [com.adham-omran.impl.protocols :as protocols]
   [clojure.java.io :as io]
   [honey.sql :as sql]
   [next.jdbc :as jdbc]))

(def db {:dbtype "sqlite"
         :dbname (io/file "./resources/sql/ir.db")})
(def ds (jdbc/get-datasource db))

(defmulti insert
  "Insert multimethod"
  :kind)

(defmethod insert :text [m]
  (->> {:insert-into [:collection]
        :columns [:afactor :priority :interval :date :kind :content]
        :values [[1.0
                  1.0
                  1.0
                  (.getEpochSecond (java.time.Instant/now))
                  "text"
                  (:content m)]]}
       sql/format
       (jdbc/execute! ds)))

(defmethod insert :web [m]
  (->> {:insert-into [:collection]
        :columns [:afactor :priority :interval :date :kind :content]
        :values [[1.0
                  1.0
                  1.0
                  (.getEpochSecond (java.time.Instant/now))
                  "web"
                  (:link m)]]}
       sql/format
       (jdbc/execute! ds)))

;; Reading

(defmulti read
  "Reading multimethod."
  :kind)

(defmethod read :web [m]
  (clojure.java.browse/browse-url (-> m :collection/content)))

(comment
  ;; Query
  (jdbc/execute! ds (sql/format {:select [:*] :from [:collection]}))

  ;; Insert text
  (-> {:kind :text
       :content "Kubernetes coordinates a highly available cluster of computers that are connected to work as a single unit"}
      insert)

  ;; Insert web link, the flow would be to open a link and extract its text into
  ;; the system.
  (-> {:kind :web
       :link "https://en.wikipedia.org/wiki/Expression_problem"}
      insert)

  ;; TODO: Change into parent like insert
  #_(-> {:kind :text
         :content "Kubernetes coordinates a highly available cluster of computers"}
        insert)

  (->> {:select [:id]
        :from [:collection]
        :where [:= "Kubernetes coordinates a highly available cluster of computers"
                :content]}
       sql/format
       (jdbc/execute! ds)
       first
       :collection/id)

  ;; Parent->Child relationship, fragment of element (in itself an element)
  (let [parent-id (->> {:select [:id]
                        :from [:collection]
                        :where [:= "Kubernetes coordinates a highly available cluster of computers that are connected to work as a single unit"
                                :content]}
                       sql/format
                       (jdbc/execute! ds)
                       first
                       :collection/id)
        child-id (->> {:select [:id]
                       :from [:collection]
                       :where [:= "Kubernetes coordinates a highly available cluster of computers"
                               :content]}
                      sql/format
                      (jdbc/execute! ds)
                      first
                      :collection/id)]
    (->> (sql/format {:insert-into [:collection_relationships]
                      :columns [:parent_id :child_id]
                      :values [[parent-id child-id]]})
         (jdbc/execute! ds)))

  ;; Open in browser
  (let [query (-> ds
                  (jdbc/execute! (sql/format {:select [:*] :from [:collection]}))
                  second)]
    (browse/browse-url (:collection/path query)))

  (jdbc/execute! ds (sql/format {:select [:*] :from [:collection]})))


;;; # Record Approach

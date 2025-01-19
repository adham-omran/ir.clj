(ns com.adham-omran.db
  "Utilities to insert to and read from db."
  (:require
   [clojure.java.browse :as browse]
   [honey.sql :as sql]
   [next.jdbc :as jdbc]))

(def db {:dbtype "sqlite" :dbname "./resources/sql/ir.db"})
(def ds (jdbc/get-datasource db))

(defmulti insert
  "Insert multimethod"
  :type)

(defmethod insert :text [m]
  (->> {:insert-into [:collection]
        :columns [:afactor :priority :interval :date :type :content]
        :values [[1.0
                  1.0
                  1.0
                  (.getEpochSecond (java.time.Instant/now))
                  "text"
                  (:content m)]]}
       sql/format
       (jdbc/execute! ds)))

(comment
  ;; Query
  (jdbc/execute! ds (sql/format {:select [:*] :from [:collection]}))

  ;; Insert text
  (-> {:type :text
       :content "Kubernetes coordinates a highly available cluster of computers that are connected to work as a single unit"}
      insert)

  (-> {:type :text
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

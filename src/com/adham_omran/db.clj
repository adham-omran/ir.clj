(ns com.adham-omran.db
  "Utilities to insert to and read from db."
  (:require
   [clojure.java.browse :as browse]
   [honey.sql :as sql]
   [next.jdbc :as jdbc]))

(def db "")

(sql/format {:select [:*] :from [:collection]})
;; => ["SELECT * FROM collection"]

(sql/format {:select [:*] :from [:collection]})


(-> {:insert-into [:properties]
     :columns [:name :surname :age]
     :values [["Jon" "Smith" 34]
              ["Andrew" "Cooper" 12]
              ["Jane" "Daniels" 56]]}
    (sql/format {:pretty true}))

(def db {:dbtype "sqlite" :dbname "./resources/sql/ir.db"})
(def ds (jdbc/get-datasource db))


;; Query
(jdbc/execute! ds (sql/format {:select [:*] :from [:collection]}))

;; Insert
(->> {:insert-into [:collection]
      :columns [:id :afactor :priority :interval :date :type :path]
      :values [[1 1.0 1.0 1.0 (.getEpochSecond (java.time.Instant/now)) "url" "https://kubernetes.io/docs/tutorials/kubernetes-basics/"]]}
     sql/format
     #_(jdbc/execute! ds))

"Kubernetes coordinates a highly available cluster of computers that are connected to work as a single unit"

(defmulti insert
  "Insert multimethod"
  :type)

(defmethod insert :text [m]
  (->> {:insert-into [:collection]
        :columns [:id :afactor :priority :interval :date :type :path]
        :values [[2 1.0 1.0 1.0 (.getEpochSecond (java.time.Instant/now)) "text" (:content m)]]}
       sql/format
       (jdbc/execute! ds)))

(comment
  ;; Insert text
  (-> {:type :text
       :content "Kubernetes coordinates a highly available cluster of computers that are connected to work as a single unit"}
      insert)

  ;; Open in browser
  (let [query (-> ds
                  (jdbc/execute! (sql/format {:select [:*] :from [:collection]}))
                  second)]
    (browse/browse-url (:collection/path query)))

  (jdbc/execute! ds (sql/format {:select [:*] :from [:collection]})))

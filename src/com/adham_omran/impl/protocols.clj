(ns com.adham-omran.impl.protocols
  (:require
   [clojure.java.browse :as browse]
   [honey.sql :as sql]))

(defprotocol Element
  (add [this])
  (read [this]))

(defrecord WebElement [link]
  Element
  (add [this]
    (println (:link this))
    (->> {:insert-into [:collection]
          :columns [:afactor :priority :interval :date :kind :content]
          :values [[1.0
                    1.0
                    1.0
                    (.getEpochSecond (java.time.Instant/now))
                    "web"
                    (:link this)]]}
         sql/format))
  (read [this]
    (browse/browse-url (:link this))))

(let [element (->WebElement "https://en.wikipedia.org/wiki/Expression_problem")]
  (read element))

;; (add temp-e)

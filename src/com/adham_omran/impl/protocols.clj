(ns com.adham-omran.impl.protocols
  (:refer-clojure :exclude [read])
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

(comment
  ;; Web Element

  (let [element (->WebElement "https://en.wikipedia.org/wiki/Expression_problem")]
    (read element))

  (let [element (->WebElement "https://www.google.com")]
    (add element)))

(defrecord TextElement [link]
  Element
  (add [this]
    (->> {:insert-into [:collection]
          :columns [:afactor :priority :interval :date :kind :content]
          :values [[1.0
                    1.0
                    1.0
                    (.getEpochSecond (java.time.Instant/now))
                    "text"
                    this]]}
         sql/format))
  (read [this]
    nil))

;; (add temp-e)

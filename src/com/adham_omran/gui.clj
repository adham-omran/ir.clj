(ns com.adham-omran.gui
  (:require [cljfx.api :as fx]))

(def gui
  (delay
    (fx/on-fx-thread
      (fx/create-component
       {:fx/type :stage
        :showing true
        :title "Cljfx example"
        :width 300
        :height 300
        :scene {:fx/type :scene
                :root {:fx/type :v-box
                       :alignment :center
                       :children [{:fx/type :label
                                   :text "Hello world"}]}}}))))

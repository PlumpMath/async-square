(ns async-square.client
  (:require [async-square.ui :as ui]
            [async-square.game :as game]
            [cljs.core.async :refer (<!)])
  (:require-macros [cljs.core.async.macros :refer (go-loop)]))

(def square
  {:x 0
   :y 0
   :w 50
   :h 50
   :v 5
   :c "rgb(255,0,0)"
   :cols ui/col-list})

(defn ^:export init
  []
  (let [input (ui/key-chan)]
    (go-loop [obj square ev (<! input)]
             (ui/draw-block obj)
             (recur (game/render obj ev) (<! input)))))
             

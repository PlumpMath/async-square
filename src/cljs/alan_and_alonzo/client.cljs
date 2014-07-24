(ns alan-and-alonzo.client
  (:require [alan-and-alonzo.ui :as ui]
            [alan-and-alonzo.game :as game]
            [cljs.core.async :refer (<!)])
  (:require-macros [cljs.core.async.macros :refer (go-loop)]))

(def alan
  {:x 0
   :y 0
   :w 50
   :h 50
   :v 5
   :c "rgb(255,0,0)"
   :cols ui/col-list})

(defn ^:export init
  []
  (.log js/console (:c alan))
  (.log js/console (take 10 ui/col-list))
  (let [input (ui/key-chan)]
    (go-loop [player alan ev (<! input)]
             (ui/draw-block player)
             (recur (game/render player ev) (<! input)))))
             

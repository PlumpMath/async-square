(ns alan-and-alonzo.ui
  (:require [cljs.core.async :refer (chan put!)]
            [domina.events :refer (listen!)]
            [domina :as dom]))

;; ---------------------------------------------
;; A groovy infinite color listing

(def freq 0.3)
(def phase1 0)
(def phase2 2)
(def phase3 4)
(def center 128)
(def width 127)

(def col-list
  (cycle
    (for [x (range 0 100) :let [r (int (+ center (* width (Math/sin (+ phase1 (* freq x))))))
                                g (int (+ center (* width (Math/sin (+ phase2 (* freq x))))))
                                b (int (+ center (* width (Math/sin (+ phase3 (* freq x))))))]]
      (str "rgb(" r "," g "," b ")"))))

;; ---------------------------------------------
;; Canvas helpers

(def canvas 
  (dom/by-id "foreground"))

(def context
  (.getContext canvas "2d"))

(defn clear-canvas
  []
  (.save context)
  (.setTransform context 1 0 0 1 0 0)
  (.clearRect context 0 0 500 500)
  (.restore context))

(defn draw-block [obj]
  (let [{:keys [x y w h c]} obj]
    (when-not (nil? obj)
      (clear-canvas)
      (set! (.-fillStyle context) c)
      (.fillRect context x y w h))))

;; ---------------------------------------------
;; User input functions

(def directions
  {37 :left
   38 :up
   39 :right
   40 :down})

(defn get-direction [input]
  (let [result (directions input)]
    (if (nil? result)
      :not-found
      result)))

(defn key-chan 
  []
  (let [ch (chan)]
    (listen! (.-body js/document) 
             :keydown 
             #(put! ch (get-direction (:keyCode %))))
    ch))

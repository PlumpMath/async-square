(ns alan-and-alonzo.game)

(defn move [obj com]
  (let [{:keys [x y w h v]} obj]
    (cond
          (= com :left) (update-in obj [:x] - v)
          (= com :right) (update-in obj [:x] + v)
          (= com :up) (update-in obj [:y] - v)
          (= com :down) (update-in obj [:y] + v)
          :else obj)))

(defn change-color [obj]
  (let [{:keys [c cols]} obj]
    (-> obj
      (assoc :c (first cols))
      (update-in [:cols] rest))))

(defn render [obj com]
  (change-color (move obj com)))

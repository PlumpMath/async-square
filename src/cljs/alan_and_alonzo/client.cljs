(ns alan-and-alonzo.client)

(defn ^:export init
  []
  (.write js/document "Hello, world!"))

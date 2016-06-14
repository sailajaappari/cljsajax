(ns cljsajax.core
  (:require [cljsajax.server :as server])
  (:gen-class))


(defn start! []
  (server/start!))

(defn -main []
  (start!))

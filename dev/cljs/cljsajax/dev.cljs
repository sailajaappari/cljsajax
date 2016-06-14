(ns cljsajax.dev
  (:require [cljsajax.core :as core]))

(enable-console-print!)

(defn on-jsload []
  (core/main))

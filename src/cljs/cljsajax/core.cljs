(ns cljsajax.core
    (:require [reagent.core :as r]
              [ajax.core :as ajax]))

(enable-console-print!)
(def uname (r/atom nil))
(def un (r/atom nil))

(defn handleClick [evt]
  (ajax/POST "/save"
             {:params @un}))


(defn root-component []
  (ajax/GET "/user" 
               {:handler (fn [data]
                           (reset! uname data))})
  (fn []
    [:div
     [:p "User Name: " @uname]
     [:div
      [:input {:type "text"
               :on-change #(reset! un (-> % .-target .-value))}]
      [:button {:on-click handleClick} "save"]]]))

(defn ^:export main []
  (r/render-component
   [root-component]
   (js/document.getElementById "app")))

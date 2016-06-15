(ns cljsajax.core
    (:require [reagent.core :as r]
              [ajax.core :as ajax]))

(enable-console-print!)
(def uname (r/atom nil))
(def items (r/atom nil))
(def p (r/atom {}))

(defn handler [data]
  (do 
    (reset! items (js->clj data))
    (js/console.log (get arr @items))))

(defn root-component []
  (ajax/GET "/user" 
               {:handler (fn [data]
                           (reset! uname data))})
  (ajax/GET "http://jsonplaceholder.typicode.com/posts/1"
     {:handler handler})
  (ajax/GET "http://jsonplaceholder.typicode.com/posts"
     {:handler (fn [data]
                   (js/console.log data))})
  (ajax/POST "http://jsonplaceholder.typicode.com/posts"
    {:params {:title "foo"
              :body "bar"
              :userId 1}
     :handler (fn [data]
                (reset! p data))})
  (fn []
    [:div
     [:p "User Name: " @uname]
     [:div "GET data: " @items]
     [:div "Post data: " @p]]))

(defn ^:export main []
  (r/render-component
   [root-component]
   (js/document.getElementById "app")))

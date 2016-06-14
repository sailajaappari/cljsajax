(ns cljsajax.server
  (:require [ring.util.response :as response]
            [compojure.route :as route]
            [compojure.core :refer [defroutes GET PUT POST]]
            [ring.middleware.transit :as transit]
            [ring.adapter.jetty :as jetty]
            )
  (:import [org.eclipse.jetty.server Server]))

(def server (atom nil))

(def users (atom {:user1 "sailaja"}))


(defroutes routes
  (GET "/" req (response/resource-response "index.html" {:root "public"}))
  (GET "/user" [] {:status 200
                   :body (:user1 @users)}
  (POST "/save" [] {:status 200
                    
                    :body (conj @users [:user2 n])}))
 
  (route/resources "/")
  (route/not-found "Not found"))


(def app
  (-> routes
      (transit/wrap-transit-response {:encoding :json})
      (transit/wrap-transit-body)))

(defn start! []
  (when-not @server
    (reset! server (jetty/run-jetty #'app {:join? false :port 8080}))))

(defn stop! []
  (when-let [s ^Server @server]
    (println "Stopping server")
    (.stop @server)
    (reset! server nil)))

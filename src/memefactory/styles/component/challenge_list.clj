(ns memefactory.styles.component.challenge-list
  (:require
   [garden.def :refer [defstyles]]
   [garden.stylesheet :refer [at-media]]
   [clojure.string :as s]
   [memefactory.styles.base.colors :refer [color]]
   [memefactory.styles.base.media :refer [for-media-min for-media-max]]
   [memefactory.styles.base.fonts :refer [font]]
   [memefactory.styles.base.fonts :refer [font]]
   [memefactory.styles.component.buttons :refer [button tag]]
   [garden.units :refer [px em pt]]))


(defstyles core
  [:.challenges.panel
    [:.loading
     {;;:flex :none
      :color (color :busy-grey)
      :border-top-color (color :white)
      :margin :auto
      :border-width (em 1)
      :border-top-width (em 1)
      :width (em 7)
      :height (em 7)
      :border-style :solid
      :border-top-style :solid
      :border-radius "50%"
      :animation-name :spin
      :animation-duration "2s"
      :animation-iteration-count :infinite
      :animation-timing-function :linear}]
   {:padding-left (em 6)
    :padding-right (em 6)}
   (for-media-max :tablet
                  [:&
                   {:padding-right (em 2)
                    :padding-left (em 2)}])
   #_[:.controls {:width (em 11)
                :margin-left :auto
                :margin-right 0}]
   [:.controls {:display :block
                :margin-top (em 4)
                :margin-left :auto
                :margin-right 0
                :width (em 11)
                :right (em 0)
                :height 0
                :top (em -3)
                :position :relative}
    [:select {:background-color :white}]
    [:.help-block {:display :none}]
    (for-media-max :tablet
                   [:&
                    {:display :inline-block
                     :height (em 1)
                     :padding-top (em 1)
                     :width "100% !important"
                     :top (em -1)}])]
   [:.challenge
    {:display :grid
     :grid-template "'info image action'"
     :grid-template-columns "1fr 1fr 1fr"
     :grid-gap (em 2)
     :background-color :white
     :margin-bottom (em 1.5)
     :border-radius (em 0.6)
     :padding (em 1.4)}
    (for-media-max :tablet
                   [:& {:grid-template "'image' 'info' 'action'"}])
    [:.info {:overflow :hidden
             :grid-area :info}
     [:h2
      {:color (color :purple)
       :text-transform :uppercase}
      (font :bungee)
      (for-media-max :tablet
                     [:& {:text-align :center}])]
     [:h3 {:color (color :menu-text)
           :font-weight :bold
           :font-size (em 1)
           :margin 0}]
     [:ol
      {:list-style-type :none
       :font-size (em 0.8)
       :padding 0
       :margin-top (em 0.4)
       :color (color :menu-text)}
      [:&.tags ;; TODO refactor out tags from here
       {:margin-top (em 2)}
       [:li (tag)]]
      [:li {:margin-bottom (em 0.2)}
       [:div {:display :flex}
        [:label {:margin-right (em 0.2)
                 :white-space :nowrap}]
        [:span {:overflow :hidden
                :white-space :nowrap
                :text-overflow :ellipsis}]]]]]
    [:div.meme-tile {:display :grid
                     :grid-area :image
                     :justify-items :center}
     [:.meme-card {:position :relative}]]
    [:.action {:margin :auto
               :grid-area :action}]]])

(ns memefactory.styles.pages.leaderboard.index
  (:require [garden.def :refer [defstyles]]
            [garden.stylesheet :refer [at-media]]
            [clojure.string :as s]
            [memefactory.styles.base.icons :refer [icons]]
            [memefactory.styles.base.borders :refer [border-top border-bottom]]
            [memefactory.styles.base.colors :refer [color]]
            [memefactory.styles.base.fonts :refer [font]]
            [memefactory.styles.base.media :refer [for-media-min for-media-max]]
            [memefactory.styles.component.search :refer [search-panel]]
            [memefactory.styles.component.panels :refer [panel-with-icon]]
            [garden.selectors :as sel]
            [garden.units :refer [pt px em rem]]
            [clojure.string :as str]))

(defstyles core
  [:.leaderboard-dankest-page
   [:.loading
    {:flex :none
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
   [:section.dankest
    {:padding-top (em 2)
     :margin-right (em 6)
     :margin-left (em 6)}
    (for-media-max :tablet
                   [:& {
                        :margin-right (em 1)
                        :margin-left (em 1)
                        }])
    [:.dankest-panel
     (panel-with-icon {:url "/assets/icons/trophy2.svg"
                       :color :leaderboard-curator-bg})]
    [:.tiles
     {:margin-top (em 2)
      :padding-top (em 2)
      :padding-bottom (em 2)
      :margin-right (em 6)
      :margin-left (em 6)
      :background-color (color :meme-panel-bg)
      :border-radius "1em 1em 1em 1em"}]]])

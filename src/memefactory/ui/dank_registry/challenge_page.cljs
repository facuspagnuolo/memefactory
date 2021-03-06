(ns memefactory.ui.dank-registry.challenge-page
  (:require
   [cljs-time.core :as t]
   [cljs-time.extend]
   [district.format :as format]
   [district.format :as format]
   [district.time :as time]
   [district.ui.component.form.input :refer [select-input with-label text-input pending-button]]
   [district.ui.component.page :refer [page]]
   [district.ui.graphql.subs :as gql]
   [district.ui.web3-tx-id.subs :as tx-id-subs]
   [goog.string :as gstring]
   [memefactory.ui.events :as memefactory-events]
   [memefactory.ui.components.app-layout :refer [app-layout]]
   [memefactory.ui.components.challenge-list :refer [challenge-list]]
   [memefactory.ui.components.panes :refer [tabbed-pane]]
   [memefactory.ui.contract.registry-entry :as registry-entry]
   [memefactory.ui.dank-registry.events :as dr-events]
   [print.foo :refer [look] :include-macros true]
   [re-frame.core :as re-frame :refer [subscribe dispatch]]
   [react-infinite]
   [reagent.core :as r]
   [reagent.ratom :refer [reaction]]
   [district.graphql-utils :as graphql-utils]))

(defn header []
  [:div.challenge-info
   [:div.icon]
   [:h2.title "Dank registry - Challenge"]
   [:h3.title "Lorem ipsum dolor sit ..."]
   [:div.get-dank-button "Get Dank"]])

(defn open-challenge-action [{:keys [:reg-entry/address]}]
  (let [form-data (r/atom {})
        open? (r/atom false)
        tx-id (str address "challenges")
        tx-pending? (subscribe [::tx-id-subs/tx-pending? {::registry-entry/approve-and-create-challenge tx-id}])
        tx-success? (subscribe [::tx-id-subs/tx-success? {::registry-entry/approve-and-create-challenge tx-id}])
        errors (reaction {:local (let [{:keys [comment]} @form-data]
                                   (cond-> {}
                                     (empty? comment)
                                     (assoc :comment "Comment shouldn't be empty.")))})]
    (fn [{:keys [:reg-entry/address]}]
      (let [dank-deposit (get-in @(subscribe [::gql/query {:queries [[:search-param-changes {:key (graphql-utils/kw->gql-name :deposit)
                                                                                             :db (graphql-utils/kw->gql-name :meme-registry-db)
                                                                                             :group-by :param-changes.group-by/key
                                                                                             :order-by :param-changes.order-by/applied-on}
                                                                      [[:items [:param-change/value]]]]]}])
                                 [:search-param-changes :items 0 :param-change/value])]
        [:div.challenge-controls
         [:div.vs
          [:img.vs-left {:src "/assets/icons/mememouth.png"}]
          [:span.vs "vs."]
          [:img.vs-right {:src "/assets/icons/mememouth.png"}]]
         (if @open?
           [:div
            [text-input {:form-data form-data
                         :id :comment
                         :errors errors
                         :placeholder "Challenge Reason..."
                         :input-type :textarea}]
            [pending-button {:pending? @tx-pending?
                             :disabled (or @tx-pending? @tx-success? (not (empty? (:local @errors))))
                             :pending-text "Challenging ..."
                             :on-click (fn []
                                         (dispatch [::memefactory-events/add-challenge {:send-tx/id tx-id
                                                                                        :reg-entry/address address
                                                                                        :comment (:comment @form-data)
                                                                                        :deposit dank-deposit}]))}
             "Challenge"]
            [:span.dank (format/format-token (/ dank-deposit 1e18)  {:token "DANK"})]]
           [:button.open-challenge {:on-click #(swap! open? not)} "Challenge"])]))))

(defmethod page :route.dank-registry/challenge []
  [app-layout
   {:meta {:title "MemeFactory"
           :description "Description"}}
   [:div.dank-registry-challenge
    [:div.challenge-header
     [header]]
    [challenge-list {:include-challenger-info? false
                     :query-params {:statuses [:reg-entry.status/challenge-period]}
                     :action-child open-challenge-action
                     :key :challenge-page
                     :sort-options [{:key "created-on" :value "Newest"}
                                    {:key "challenge-period-end" :value "Challenge period end"}]}]]])

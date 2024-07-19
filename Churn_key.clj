
(config

(text-field
:name "appID"
:label "App ID"
:placeholder "Enter an api ID"
:required true)

(password-field
:name "apiKey"
:label "Api KEY"
:placeholder "Enter your API Key here"
:required true)
)

(default-source
(auth/apikey-custom-header
:headerName "x-ck-api-key"
)
(headerName "x-ck-app" "{x-ck-app}") 
(http/get :base-url "https://api.churnkey.co/v1/data")
(error-handler 
(when :status 401 :action fail)
)
)



(entity session
(api-docs-url "https://docs.churnkey.co/data-api#block-f1c1481165ba4790a66446de709c6709")

(source (http/get :url "/sessions")
(extract-path ))

(fields
id :id
org
blueprintId
segmentId
abtest
provider
aborted
canceled
surveyId
surveyChoiceId
surveyChoiceValue
feedback
discountCooldownApplied
customActionHandler
mode
createdAt
updatedAt
recordingEndTime
recordingStartTime
)

(dynamic-fields
(flatten-fields
(fields
id
email
created
subscriptionId
subscriptionStart
subscriptionPeriodStart
subscriptionPeriodEnd
currency
planId
planPrice
itemQuantity
billingInterval
billingIntervalCount
)
:from customer)


(flatten-fields
(fields
guid
offerType
pauseInterval
pauseDuration
)
:from acceptedOffer))
(relate
(contains-list-of SESSION_PRESENTED_OFFER :inside-prop "presentedOffers")
)

)

(entity SESSION_PRESENTED_OFFER

(fields
guid :id 
accepted
presentedAt
declinedAt
surveyOffer
offerType
mode
createdAt
updatedAt
recordingEndTime
recordingStartTime
)

(dynamic-fields
(flatten-fields
(fields
maxPauseLength
pauseInterval

)
:from presentedOffer)
(flatten-fields
(fields
couponId

)
:from discountConfig)
)

(relate
(needs session :prop "id")))



---------------------------------------------------------------------------------
(entity 
(field  
(contains-list-of ERP :inside-prop "coAuthors" :as "name")))

enitiy ERP(
    fields (
        name
    )
)

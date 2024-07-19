


#360 learning
#get courses
"""
AUTH-
API key as query parameter
API key as header parameter
OAuth 2.0 token

No pagination
sync-
since(path)
Modified-range(getUsersPathSessionStats)
"""

(config

(text-field
:name "companyId"
:label "Comapany ID"
:placeholder "Enter your Comapany ID"
:required true)

(password-field
:name "queryParamValue"
:label "Api KEY"
:placeholder "Enter your API Key here"
:required true)
)

(default-source
(auth/apikey-query-param 
:queryParamName "apiKey"
)
(http/get :base-url "https://app.360learning.com/api/v1")
(error-handler 
(when :status 401 :action fail)
)
)


(entity COURSE
(api-docs-url "https://api.360learning.com/#8d8606f4-301c-48eb-af85-989c7d9ba5d4")

(source (http/get :url "/courses")
(extract-path ""))

(fields
id :id :<= "_id"
author
"creation_date" :<= "creationDate"
"default_lang" :<= "defaultLang"  
description
lang
"modification_date" :<= "modificationDate" 
name
"source_lang" :<= "sourceLang"
status
type
"course_duration" :<= "courseDuration"
group
group_name :<= "groupName"
reaction_score :<= "reactionScore"
relevance_score :<= "relevanceScore")

(relate
(contains-list-of COURSE_AUTHOR :inside-prop "coAuthors" :as author)
)
(relate
(contains-list-of COURSE_TRANS_LANG :inside-prop "translationsLangs")
)
)

(entity COURSE_AUTHOR
(fields
 author :id
 )
(relate
(needs COURSE :prop "id" ))
)

(entity COURSE_TRANS_LANG
(fields
index :id :index
lang 
published
)
(relate
(contains-list-of COURSE_TRANS_LANG_TRANSLATOR :inside-prop "translators" :as translator)
(needs COURSE :prop "id"))

)
(entity COURSE_TRANS_LANG_TRANSLATOR
fields(
    translator :id
)
(relate
(needs COURSE_TRANS_LANG :prop "course_id" :as "course_id")
(needs COURSE_TRANS_LANG :prop "index"))
)


--------------------------------------------------------------------------------
(entity GROUPS
(api-docs-url "https://api.360learning.com/#8d8606f4-301c-48eb-af85-989c7d9ba5d4")

(source (http/get :url "/groups")
(extract-path ""))

(fields
id :id :<= "_id"
custom
name
public)

(relate
(contains-list-of GROUP_ADMIN :inside-prop "admins")
(contains-list-of GROUP_AUTHOR :inside-prop "authors")
(contains-list-of GROUP_COACH :inside-prop "coaches")
(contains-list-of GROUP_USER :inside-prop "users")
))

(entity GROUP_ADMIN
(fields
id :id :<= "_id"
mail
)
(relate
(needs GROUP :prop "id": as "group_id"))
)

(entity GROUP_AUTHOR
(fields
id :id :<= "_id"
mail
)
(relate
(needs GROUP :prop "id": as group_id))
)

(entity GROUP_COACH
(fields
id :id :<= "_id"
mail
)
(relate
(needs GROUP :prop "id" : as group_id))
)

(entity GROUP_USER
(fields
id :id :<= "_id"
mail
)
(relate
(needs GROUP :prop "id" : as group_id))
)





-------------------------------------------------------
(entity PROGRAMTEMPLATE
(api-docs-url "https://api.360learning.com/#8d8606f4-301c-48eb-af85-989c7d9ba5d4")

(source (http/get :url "/programs/templates")
(extract-path ))

(fields
id :id :<= "_id"
name
description
author
)
(relate
(contains-list-of PROGRAM_TEMP :inside-prop "elements")))

(entity PROGRAM_TEMP
(fields
type
id :id :<= "_id"
title
)
(relate
(needs PROGRAMTEMPLATE :prop "id"))
)
 ----------------

 (entity COURSE_STAT
(api-docs-url "https://api.360learning.com/#8d8606f4-301c-48eb-af85-989c7d9ba5d4")

(source (http/get :url "/courses/{COURSE.id}/stats")
(extract-path "attempts"))

(fields
id :id :<= "_id"
author
creation_date :<= "creationDate"
default_lang :<= "defaultLang"  
description
lang
modification_date :<= "modificationDate" 
name
source_lang :<= "sourceLang"
status
type
course_duration :<= "courseDuration"
group
group_name :<= "groupName"
reaction_score :<= "reactionScore"
relevance_score :<= "relevanceScore")

(relate
(includes COURSE :prop "id")
(contains-list-of COURSE_AUTHOR :inside-prop "coAuthors" :as author)
)
(relate
(contains-list-of COURSE_TRANS_LANG :inside-prop "translationsLangs")
)
)



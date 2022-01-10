# College-App
# Android-Study-Jams

College Data Storage System

<b> Problem Statement: </b>

Over a great period, many issues have been faced by our students due to college in online mode. 
A proper system to store data such as :
- [x] Notice
- [x] Images of different events organised by the institute 
- [x] Ebooks uploaded by the professors and classmates 
Technology plays a vital role in day-to-day life activities which in turn made great changes in many work fields and out of them Mobile Application is one of the major developments. Mobile Application can be used effectively for this job as they are widely used and are known for easy access.

<b> Proposed Solution : </b>

This project proposes a “College Data Storage System” to keep track of the college information.
Its features include:
- [x] Uploading , Displaying and Deleting of Notice (A substitute for physical notice boards)
- [x] Announcements of future events and Pics of Past Events 
- [x] Uploading and Displaying of Ebooks (A substitute for Hard copies of notes)
- [x] Inserting, Updating and Displaying of Faculty Info
<br>
<b> Home Page UI </b>
<br>
<img src = "images/HomePageUI.jpeg" width = 200>
<br>
<br>
<b> Display Notice UI </b>
<br>
<img src = "images/DisplayNotice.jpeg" width = 200/>

<img src = "images/DisplayNotice.jpeg" width = 200>
<br>  	  	
<b> Functionality & Concepts used : </b>

- The App has a very simple and interactive interface which helps the students select their route bus and track its location. Following are few android concepts used to achieve the functionalities in app : 
- Constraint Layout : Most of the activities in the app uses a flexible constraint layout, which is easy to handle for different screen sizes.
- Simple & Easy Views Design : Use of familiar audience EditText with hints and interactive buttons made it easier for students to register or sign in without providing any detailed instructions pages. Apps also uses App Navigation to switch between different screens.
- RecyclerView : To present the list of different route busses we used the efficient recyclerview.
Google Maps API : We are also using the Google Maps API free version for  below 1000 users. In future if the user base increases we will go for the upgraded plan too.
- LiveData & Room Database : We are also using LiveData to update & observe any changes in the Bus driver's locations received from their mobile at real time and update it to local databases using Room. Coordinates are then updated in the bus route screen and students can track their route bus locations.

<b> Application Link & Future Scope : </b>

The app is currently in the Alpha testing phase with GNITS institute with a limited no. of users, You can access the app : [YOUR APP LINK HERE](either Github link or Google Play store link of published app or .apk file).

Once the app is fully tested and functional in GNITS institute, we plan to talk to neighboring colleges also to propose this app idea and collaborate with them on this transportation service. We aim that by next year most of the colleges in our district will use Bus tracking apps to check out their bus routes and be informed all the time. Also we are planning to include emergency SOS features for students traveling in busses if they are stuck somewhere. 

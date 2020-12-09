# CleanCommunity

Curtis Ching                  n01274536  
Kevin Daniel Delgado Toledo   n01323567  
Manpreet Parmar               n01302460  

https://github.com/Manpreet7724/CleanCommunity

Team cleanup developed an community run app in order to dispose of garbage in areas that are subject to littering. The app lets users report littering levels in an area, where users are able to set the radius and severity of trash/litter in that particular area and give it a name depending on the address of that area. This allows other users to join up and volunteer to clean up that area. The goal is to unite the community to create an interest in cleaning up our planet, generating data with visual representation of how much trash there is on our planet, in order to inspire large corporations, groups, and the government to increase attention and funding for eliminating littering. 


# Instructions
In order to make the app work some things are needed.  
1- debug.keystore key file.  
2- A string holding your Google Api Key (necessary to display the map).  
3- A string holding your client id (necessary for gmail authentication).  

We will grant you with step by step instructions in how to apply these in order to successfully debug the app:   
1. Get the debug.keystore file stored in the zipfile.  
2. Go to File>Project Structure.
3. In Modules access Signing Config and inside the debug key, make sure the fields are as follow.  
->Store File: "here input the path to the given debug.ks file"  
->Store Password: "cleancommunity"  
->Key Alias: "debug"  
->Key Password: "cleancommunity"  

![image](https://user-images.githubusercontent.com/71301117/101694941-de317300-3a41-11eb-9972-1ff61747b99d.png)  


4. Then access Build Variants, and inside Build Types, assure that the field Signing Config is filled as "$signingConfigs.debug".  

![image](https://user-images.githubusercontent.com/71301117/101695431-8c3d1d00-3a42-11eb-9172-e64095f93df5.png)  

5. Another way of doing this is making sure that inside the Module build.gradle you can find this.  
```  
android {  
...  
    signingConfigs{  
       debug {  
          storeFile file('Path to the debug.ks file goes here')  
          storePassword 'cleancommunity'  
          keyAlias 'debug'  
          keyPassword 'cleancommunity'  
       }  
    }  
}
```

6. After doing this and syncing your gradle files, the debug key should be implemented to work with our app.  
7. An xml file called pass.xml should be inside the zip file, this xml file holds two strings, one with your client id for google signup and one with your google api key for google maps and the program should run correctly.  

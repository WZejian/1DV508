Cookbook
DB Connection
Reviewer! Feel free to launch and use the app without further configuration as it can be used with a remote DB. Please note that if you choose to do so, you will experience slow speeds as it is a low-bandwidth server hosted in China.

Alternativly, you will find a config Java file in the main app called DBConnector where you can configure your local instance. (default: 127.0.0.1:3306). A clone of the database is uploaded to SQLDump folder for your convinience.

Admin account
Please feel free to use the admin account:

username: admin
password: AdminIsTobias
Passwords are hashed and therefore case sensitive. Alternative standard user is:

username: Marek
password: AluminiumPork
Other Information
Database table users
To avoid confusing, it is essential to explain what each column means, because we experienced redesign of this table.

database col	real meaning
id	unique, auto increment
name	username, unique, used in login, cannot changed by standard user
pass_hash	the hash value of password
admin	0 stands for standard user, 1 stands for admin user
login_name	This is NOT login name, this is display name (nickname) in program and can be changed by standard user
Gitlab Branches
We worked in different branches, most of branches were named by issues, such as "Help scene", "Shopping List scene". If a function in branch was useable (not failing the ./gradlew run) then we merged it to branch development. When the issue was done, we delete the branch.

We only merged development into main a few times for big updates. main is the final product.

Website
We had a website for this program. Although it is not required, we did it for fun.

http://meichen.tech/cookbook.

Reference
Some of the reference are put here, while the rest are in code files.

https://stackoverflow.com/questions/7555564/what-is-the-recommended-way-to-make-a-numeric-textfield-in-javafx

http://www.tutorialsface.com/2016/12/how-to-make-numeric-decimal-textfield-in-javafx-example-tutorial/

https://code.makery.ch/blog/javafx-dialogs-official/

https://o7planning.org/11079/javafx-tableview

http://www.java2s.com/Tutorials/Java/JavaFX/0650__JavaFX_TableView.htm

https://docs.oracle.com/javase/tutorial/2d/images/drawonimage.html

https://examples.javacodegeeks.com/desktop-java/imageio/create-image-file-from-graphics-object/

https://docs.oracle.com/javase/tutorial/2d/images/saveimage.html

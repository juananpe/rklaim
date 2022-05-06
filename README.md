# rklaim
Rklaim / Use Case: Process Claim

* Execute the application. It will create by default a new Claim with ID:4 assigned to the Officer with ID:1
* Add some actions to the claim or change the resolution. It should work.
* Now, Change the officer ID and try to get info about the same claim. Then, try to add an action or change resolution: it should inform you that you can't do that (you are not the officer assigned to the claim)

Edit `config/config.xml` file to open the DB in open mode (by default it will be opened in initialize mode)

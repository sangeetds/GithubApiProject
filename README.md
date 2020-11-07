# GithubApiProject
A CLI which displays top committees of popular repositories of Github organisations. The name of the organisation, the number of repositories to be displayed and the number of committees to be displayed are entered when prompted. 

# How to Run the program
Please ensure the JRE and gradle is installed in your local machine. The rest of the dependecies are installed by gradle. 

Please run this command when you are running the program for the first time to ensure a smooth build. 
```
gradle build
```

On succesful build, to run the CLI without providing user credential (The Github API limits non authorized requests to 60 requests per hour)
```
./project
```

To run the CLI with user credentials (Authorized requests are limited to 5000 requests per hour)
```
./project --login=username --token=tokenOrPassword
```

Where the 'username' is login id of your github account and 'tokenOrPassword' is either the token generated for your github account or the password for your github account.


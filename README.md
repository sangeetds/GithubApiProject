# GithubApiProject
A CLI written in Kotlin which displays top committees of popular repositories of Github organizations. The name of the organization, the number of repositories to be displayed and the number of committees to be displayed are entered when prompted. 

# How to Run the program
Please ensure the JDK(15 and above) , Kotlin(1.4 and above) and Gradle(v 6.3 and above) is installed on your local machine. The rest of the dependencies are installed by gradle. 

Please run this command when you are running the program for the first time to ensure a smooth build. 
```
gradle build
```

On successful build, to run the CLI without providing user credential (The Github API limits non authorized requests to 60 requests per hour)
```
./project
```

To run the CLI with user credentials (Authorized requests are limited to 5000 requests per hour)
```
./project --login=username --token=tokenOrPassword
```

Where the 'username' is the login id of your Github account and 'tokenOrPassword' is either the token generated for your Github account or the password for your Github account.

# Sample run of the CLI

Here are a few sample run of the app run with instructions provided above. 

Sample run without login credentials
![alt text](https://github.com/sangeetds/GithubApiProject/blob/main/images/test_two.png)

Sample run with login credentials provided.
![alt text](https://github.com/sangeetds/GithubApiProject/blob/main/images/test_three.png)


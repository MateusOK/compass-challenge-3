# Asynchronous API | Compass 3rd Challenge

## Project Objective

The primary goal of this project is to interact with an external API in an asynchronous manner, utilizing message brokers like ActiveMQ Artemis. 
This approach enables us to maintain a comprehensive history of request handling, categorized into various states:

- CREATED: Represents the initial state of a newly created post.
- POST_FIND: Signifies the app's ongoing search for essential post data.
- POST_OK: Indicates the successful retrieval of basic post data.
- COMMENTS_FIND: Marks the app's process of searching for comments related to a post.
- COMMENTS_OK: Denotes the availability of post comments after retrieval.
- ENABLED: Represents the successful processing and activation of a post.
- DISABLED: Indicates the deactivation of a post, whether due to processing issues or user choice.
- UPDATING: Highlights the need for reprocessing and updating of a post.
- FAILED: Points to an error or failure that occurred during processing.
  
By implementing this workflow, we aim to efficiently manage requests, ensure thorough processing, and maintain a clear status history for each post's handling.

## How to Prepare the Environment?

### IDE

To begin, it's important to have an Integrated Development Environment (IDE) installed on your machine. You can choose to install IntelliJ IDEA, 
which is a popular IDE for Java development. You can download and install IntelliJ IDEA by visiting their official [website](https://www.jetbrains.com/idea/download/).

If you come across any issues or face challenges during the installation process, you can refer to a helpful video tutorial on YouTube that provides step-by-step instructions on how to install the IDE.

[![Video](https://i.ytimg.com/vi/viNG3VVnzFE/hq720.jpg)](https://www.youtube.com/watch?v=viNG3VVnzFE)

---

### Attention!

Once you have your IDE installed, it's essential to ensure that you have the Java JDK (Java Development Kit) installed as well. When you create a new project in IntelliJ IDEA, it provides an option to install the JDK automatically.

It's important to note that you should install version 17 or a more recent version of the JDK to ensure compatibility with the project.

By verifying the installation of the Java JDK and ensuring that it's at least version 17, you'll be ready to proceed with your development tasks using IntelliJ IDEA.

![image](https://github.com/MateusOK/ecommerce-compass-challenge-1/assets/84550655/e18ad64e-d7d8-4d14-b4dd-d2edc7506c67)

---

##Testing the API

To test the API there are two methods, using the Swagger documentation or with Postman.

---

### Swagger

To use Swagger you just need to follow this [link](http://localhost:8080/swagger-ui/index.html).

---

### Postman

Almost ready! Once you have the IDE installed, the next step is to install Postman, which is a popular tool for testing API endpoints. To download Postman, simply visit their official website at [https://www.postman.com/downloads/](https://www.postman.com/downloads/) and follow the instructions provided.

By installing Postman, you'll have a user-friendly interface that allows you to send requests to your API and inspect the responses. It's a valuable tool for testing and debugging API endpoints.

Make sure to have Postman installed before proceeding with testing the API.  

---

## All set!

Now you're all set to test the API! To get started, simply run the application and use the endpoints provided below. Enjoy exploring the functionalities!

---

## Endpoints

### Postman Collection<br>
To simplify the testing process, we have provided a Postman collection that contains all the API endpoints. You can download the collection from the following link: [Download Postman Collection](https://github.com/MateusOK/compass-challenge-3/releases/tag/PostmanCollection)

#### How to Use

1. Once you have the Postman collection downloaded, open Postman.
2. Click on the "Import" button in the top-left corner of the Postman application.
3. In the import dialog, select the "File" tab and click on "Upload Files".
4. Choose the downloaded Postman collection file.
5. The collection will be imported into Postman, and you will see all the available endpoints listed.

#### Testing the Endpoints

1. With the imported collection open, you can navigate through the available folders and endpoints to view their details.
2. Each endpoint in the collection includes information such as the HTTP method, request URL, request parameters, headers, and example request/response bodies.
3. To test an endpoint, select it from the collection and click on the "Send" button.
4. Observe the response received in the "Response" section to verify the API's behavior.
   
Please note that you may need to adjust the request parameters and headers based on your specific testing requirements.

That's it! You're all set to start testing the API endpoints using the provided Postman collection. If you encounter any issues or have questions, feel free to reach out for assistance.

Happy testing!

---

### • GET

#### GET all Posts: 

  To get all posts in the database with their history and comments you'll need to send a GET request to
  
```
  http://localhost:8080/posts
```

---

### • POST

#### Save a Post:

  To save a new book you'll need to provide the ID of the desired post since we're consuming from an external api.

  Posts go from 1 to 100.

```
  http://localhost:8080/posts/{postId}
```

Make sure to replace *{id}* with the post ID.

---

### • PUT

### Update a post:

  To update an existing post you'll need to provide the ID of the desired post, to do it just send a PUT request to

```
  http://localhost:8080/posts/{postId}
```

Make sure to replace *{id}* with the post ID.

---

### • DELETE

#### DISABLE a post:

  To disable an existing post, you'll just need to provide the post ID that you want to disable and send a DELETE request to:

```
  http://localhost:8080/posts/{postId}
```
Make sure to replace *{id}* with the post ID you want to delete.

If the deletetion was successful you'll receive a http status of: **204 No Content**

---

# Technologies

In this project, the following techlogies were used:

- Java
- SpringBoot
- H2
- Maven
- ActiveMQ Artemis

---

# Thanks!

I would like to express my heartfelt gratitude to @Compass.UOL for granting me this incredible opportunity to showcase my skills and knowledge. I am genuinely thankful for this challenge and eagerly anticipate upcoming opportunities.

I extend my sincere thanks to the entire team at @Compass.UOL for their unwavering support and invaluable guidance throughout this journey. It has been an exceptional period of growth and learning, and I am truly grateful for the chance to put my abilities to the test.

I am enthusiastic about the forthcoming challenges and am eager to contribute my very best to the success of @Compass.UOL. Thank you once again for this exceptional opportunity, and I am eagerly looking forward to what the future has in store.

## Author

Developed by:<br>
<a href="https://www.linkedin.com/in/mateus-ribeiro-1779491a7/">Mateus Ribeiro</a><br>

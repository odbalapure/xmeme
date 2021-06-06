# XMEME 

### Functionalities
1. Guest users can see and search memes based on captions or owner name
2. Registered users can post memes
3. Admins can edit/delete memes
4. Admins can activate/deactivate users 

### Technologies 
> Spring Boot, Spring Security, Jackson, MySQL, MongoDB 

### Use case diagram
![Xmeme - Use Case](https://user-images.githubusercontent.com/83666636/120932998-d105f000-c715-11eb-823f-fe45640942bc.png)

### Class Diagram
![Xmeme - Class Diagram](https://user-images.githubusercontent.com/83666636/120933007-da8f5800-c715-11eb-9b98-33c6ee35133e.png)

### REST Endpoints

|   Description                 |  Mappings                   |  Input        |
|  ------------                 | -----------                 | --------------|
| Get list of all memes         |  /xmeme/memes               |             
| See a specific memem          |  /xmeme/meme/**             |
| Register User                 |  /user/register             | 
| Swagger Documentation         |  /v2/api-docs               |
| Delete Meme                   |  /xmeme/delete/**           | 
| 

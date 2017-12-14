REGISTRATION

POST /api/user/registration HTTP/1.1  
Host: localhost:49000  
Accept: application/json  
Content-Type: application/json  
Cache-Control: no-cache  
Postman-Token: ba1fba30-9bbf-6413-227d-da1b3bdb306f  

{"name":"Kamil Lasecki",  
"email":"kamil.lasecki@eircom.net",  
"address":{  
	"street":"4 La Touche drive, Bluebell",  
	"city":"Dublin",  
	"county":"Dublin"},  
"login":"klasecki1",  
"password":"12345"}  

LOGIN

POST /api/user/login HTTP/1.1  
Host: localhost:49000  
Accept: application/json   
Content-Type: application/json  
Cache-Control: no-cache  
Postman-Token: ea697795-842a-d4a6-b318-a6a7006c6359  
  
{  
	"login" : "klasecki1",   
	"password" : "12345"  
}  


<p>
  <b>API name:</b></b> getBooks() <br/>
  <b>Description:</b></b> This allows to return all books available in the system <br/>
  <b>URI:</b></b> localhost:49000/api/books/ <br/>
  <b>HTTP verb:</b></b> GET<br/>
  <b>Headers:</b></b> Accept: application/xml (for output in XML) or application/json (for output in JSON) <br/>
  <b>Parameters:</b></b> none<br/>
  <b>Resource contents:</b></b> ArrayList of books in JSON or XML format (depends on HEADER set) <br/>
  <b>Pre-Conditions:</b></b> none <br/>
  <b>Post-conditions:</b></b> all books are returned <br/>
</p>

<p>
  <b>API name:</b></b> getBook(int id) <br/>
  <b>Description:</b></b> This allows to find a book with specified ID  <br/>
  <b>URI:</b></b> localhost:49000/api/books/book/{id} <br/>
  <b>HTTP verb:</b></b> GET <br/>
  <b>Headers:</b></b> Accept: application/xml (for output in XML) or application/json (for output in JSON) <br/>
  <b>Parameters:</b></b> id (int, required) <br/>
  <b>Resource contents: </b></b>a book in JSON or XML format (depends on HEADER set) <br/>
  <b>Pre-Conditions:</b></b> book with specified ID should exist <br/>
  <b>Post-conditions:</b></b> book with specified id is returned  <br/>
</p>

<p>
  <b>API name:</b> createBook(Book book) <br/>
  <b>Description:</b> This allows to create a new book <br/> 
  <b>URI:</b> localhost:49000/api/books/book <br/>
  <b>HTTP verb:</b> POST <br/>
  <b>Headers:</b> Content-Type: application/json <br/>
  <b>Parameters:</b> book (
  <code>{"author":"book_author", <br/>
  "title":"title_of_new_book", <br/>
  "publisher":"publisher_name"}</code>, required) <br/>
  <b>Resource contents:</b> status, URI of newly created book <br/>
  <b>Pre-Conditions:</b> none <br/>
  <b>Post-conditions:</b> book with supplied details is added <br/>
</p>

<p>
  <b>API name</b>: removeBook(long id) <br/>
  <b>Description:</b> This allows to delete a book of given ID <br/> 
  <b>URI:</b> localhost:49000/api/books/book/{id} <br/>
  <b>HTTP verb:</b> DEETE <br/>
  <b>Headers:</b> none <br/>
  <b>Parameters:</b> id (int, required) <br/>
  <b>Resource contents:</b> deleted book, status <br/>
  <b>Pre-Conditions:</b> book with specified ID should exist <br/>
  <b>Post-conditions:</b> book with specified id is deleted <br/>
</p>

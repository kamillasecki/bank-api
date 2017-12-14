REGISTRATION  
========================  
POST /api/user/registration HTTP/1.1  
Host: localhost:49000  
Accept: application/json  
Content-Type: application/json  
  
{"name":"Kamil Lasecki",  
"email":"kamil.lasecki@eircom.net",  
"address":{  
	"street":"4 La Touche drive, Bluebell",  
	"city":"Dublin",  
	"county":"Dublin"},  
"login":"klasecki1",  
"password":"12345"}  
  
LOGIN
========================  
POST /api/user/login HTTP/1.1  
Host: localhost:49000  
Accept: application/json   
Content-Type: application/json  
  
{  
	"login" : "klasecki1",   
	"password" : "12345"  
}  
  
LOGOUT
========================  
GET /api/user/32768/logout HTTP/1.1  
Host: localhost:49000  
Authorization: huls3mhvnqlr8uju2pf09b36c9  
  
ADD NEW ACCOUT
========================  
POST /api/user/32768/account/new HTTP/1.1  
Host: localhost:49000  
Accept: application/json  
Content-Type: application/json  
Authorization: o32vj4d3g3vg0lrfc83tb166rp  
  
{  
	"name" : "Savings"  
}  
  
ACCOUNT TOPUP
========================  
POST /api/user/32768/account/31983797/addMoney HTTP/1.1  
Host: localhost:49000  
Accept: application/json  
Content-Type: application/json  
Authorization: o32vj4d3g3vg0lrfc83tb166rp  
  
{  
	"amount" : 1000 
}  
   
GET USER BY ID
========================  
GET /api/user/32768 HTTP/1.1  
Host: localhost:49000  
Accept: application/json  
Content-Type: application/json  
Authorization: o32vj4d3g3vg0lrfc83tb166rp  
  
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

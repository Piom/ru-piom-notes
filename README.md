# ru-piom-notes
Rest Documentation

**Show all account notes**
----
  Returns json data about a single note by account id.

* **URL**

  /:accountname/notes

* **Method:**

  `GET`

*  **URL Params**

   **Required:**

   `accountname=[string]`

* **Data Params**

  None

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** `{ id : 12, content : "Michael Bloom" }`

* **Error Response:**

  * **Code:** 404 NOT FOUND <br />
    **Content:** `{ error : "Note doesn't exist" }`



* **Sample Call:**

  ```javascript
    $.ajax({
      url: "/account/notes",
      dataType: "json",
      type : "GET",
      success : function(r) {
        console.log(r);
      }
    });
  ```
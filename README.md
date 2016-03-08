# ru-piom-notes
Rest Documentation

**Show all user notes**
----
  Returns json data about a single note by user id.

* **URL**

  /:username/notes

* **Method:**

  `GET`

*  **URL Params**

   **Required:**

   `username=[string]`

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
      url: "/user/notes",
      dataType: "json",
      type : "GET",
      success : function(r) {
        console.log(r);
      }
    });
  ```
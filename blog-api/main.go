package main

import (
	"database/sql"
	"encoding/json"
	"fmt"
	_ "github.com/go-sql-driver/mysql"
	"github.com/gorilla/mux"
	"log"
	"net/http"
	"strconv"
)

// BlogPost struct (Model)
type BlogPost struct {
	ID	string `json:"id"`
	Title string `json:"title"`
	Text string `json:"text"`
}

// get all books
func getBlogs(w http.ResponseWriter, r *http.Request) {
	w.Header().Set("Content-Type", "application/json")

	result, err := db.Query("select * from blog")

	var blogs []BlogPost

	if err != nil {
		fmt.Println("Error in get blogs")
	} else {

		for result.Next() {
			var blog BlogPost

			err = result.Scan(&blog.ID, &blog.Title, &blog.Text)

			if err != nil {
				fmt.Println("Error in get blogs")
			} else {
				blogs = append(blogs, blog)
			}
		}

		json.NewEncoder(w).Encode(blogs)

	}

}

// get one book
func getBlog(w http.ResponseWriter, r *http.Request) {
	w.Header().Set("ContentType", "application/json")
	params := mux.Vars(r)
	// loop through books and find with id

	var blog BlogPost

	err := db.QueryRow("select * from blogs where id = ?", params["id"]).Scan(&blog.ID, &blog.Title, &blog.Text)
	if err != nil {
		fmt.Println("error in get blog")
		json.NewEncoder(w).Encode(&BlogPost{})
	} else {
		json.NewEncoder(w).Encode(blog)
	}


}

// create book
func createBlog(w http.ResponseWriter, r *http.Request) {
	var blog BlogPost
	json.NewDecoder(r.Body).Decode(&blog)


	insert, err := db.Exec("insert into blog (title, text) values (?, ?)", blog.Title, blog.Text)

	if err != nil {
		fmt.Println("Error in insert")
		json.NewEncoder(w).Encode("Error")
	} else {
		res, _ := insert.LastInsertId()
		blog.ID= strconv.FormatInt(res, 10)
		json.NewEncoder(w).Encode(blog)
	}

}

// update book
func updateBlog(w http.ResponseWriter, r *http.Request) {

}

// delete book
func deleteBlog(w http.ResponseWriter, r *http.Request) {

}

// init books slice

var books []BlogPost

var db *sql.DB = nil
var err error = nil


func main() {
	// init router
	r := mux.NewRouter()

	db, err = sql.Open("mysql", "root:password@tcp(127.0.0.1:8001)/blog")
	if err != nil {
		fmt.Println("error in db")
	}

	// route handlers
	r.HandleFunc("/api/blog", getBlogs).Methods("GET")
	r.HandleFunc("/api/blog/{id}", getBlog).Methods("GET")
	r.HandleFunc("/api/blog", createBlog).Methods("POST")
	r.HandleFunc("/api/blog/{id}", updateBlog).Methods("PUT")
	r.HandleFunc("/api/blog/{id}", deleteBlog).Methods("DELETE")

	defer db.Close()

	log.Fatal(http.ListenAndServe(":8000", r))

}

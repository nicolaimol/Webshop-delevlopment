package main

import (
	"encoding/json"
	"fmt"
	"github.com/gorilla/mux"
	"net/http"
	"strconv"
)

// BlogPost struct (Model)
type BlogPost struct {
	ID	string `json:"id"`
	Title string `json:"title"`
	Text string `json:"text"`
	Date string `json:"date"`
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

			err = result.Scan(&blog.ID, &blog.Title, &blog.Text, &blog.Date)

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
	w.Header().Set("Content-Type", "application/json")
	params := mux.Vars(r)
	// loop through books and find with id

	var blog BlogPost

	err := db.QueryRow("select * from blog where id = ?", params["id"]).Scan(&blog.ID, &blog.Title, &blog.Text)
	if err != nil {
		fmt.Println("error in get blog")
		json.NewEncoder(w).Encode(&BlogPost{})
	} else {
		json.NewEncoder(w).Encode(blog)
	}


}

// create book
func createBlog(w http.ResponseWriter, r *http.Request) {
	w.Header().Set("Content-Type", "application/json")
	w.Header().Set("Access-Control-Allow-Headers", "*")
	var blog BlogPost
	json.NewDecoder(r.Body).Decode(&blog)

	var id string

	uuid := r.Header.Get("Auth")
	if uuid == "" {
		http.Error(w, "auth error", http.StatusForbidden)
	} else {
		err := db.QueryRow("select id from user where id = ? && last > now()", uuid).Scan(&id)
		if err != nil {
			http.Error(w, "Auth over", http.StatusForbidden)
		} else {
			db.Query("update user set last = date_add(now(), interval 30 minute )")

			insert, err := db.Exec("insert into blog (title, text, date) values (?, ?, CURDATE())", blog.Title, blog.Text)

			if err != nil {
				fmt.Println("Error in insert")
				json.NewEncoder(w).Encode("Error")
			} else {
				res, _ := insert.LastInsertId()
				blog.ID= strconv.FormatInt(res, 10)
				json.NewEncoder(w).Encode(blog)
			}
		}
	}


}

// update book
func updateBlog(w http.ResponseWriter, r *http.Request) {

}

// delete book
func deleteBlog(w http.ResponseWriter, r *http.Request) {

}

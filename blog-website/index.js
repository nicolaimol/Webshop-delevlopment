const homeEl = $("#home");
const postsEl = $("#posts");
const newPostsEl = $("#new")

const home = () => {
    homeEl.removeClass("hidden")
    postsEl.addClass("hidden");
    newPostsEl.addClass("hidden");
}

const posts = () => {
    postsEl.removeClass("hidden");
    homeEl.addClass("hidden");
    newPostsEl.addClass("hidden");

    $("#post-content").html("")

    $.get("http://localhost:8000/api/blog", (data) => {
        //blogs = data;

        for (let blog of data) {
        let content = `<div class="card"><h3>${blog.title}</h3>`
        content += `<p>${blog.text}</p>`
        content += `<p>${new Date(blog.date).toLocaleDateString('no-NO', {day: 'numeric', month: 'long', year: 'numeric'})}</p></div>`
        $("#post-content").append(content)
    }
    })

}

const newPost = () => {
    newPostsEl.removeClass("hidden");
    homeEl.addClass("hidden");
    postsEl.addClass("hidden");

    if (sessionStorage.getItem("id")) {
        $("#new-post").removeClass("hidden");
        $("#login").addClass("hidden");
    } else {
        $("#new-post").addClass("hidden");
        $("#login").removeClass("hidden")
    }
}

const postBlog = () => {
    const obj = {
        id: "0",
        title: $("#blog-title").val(),
        text: $("#blog-text").val(),
    }

    $.ajax({
        url: "http://localhost:8000/api/blog",
        type: 'POST',
        headers: {
            "Auth": sessionStorage.getItem("id"),
            "Content-Type": "application/jsonp"
        },
        data: JSON.stringify(obj),
        success: data => {
            console.log(data);
            posts();
        },
        error: data => {
            console.log(data);
            if (data.statusText === "Forbidden") {
                console.log(data)
                sessionStorage.removeItem("id");
                newPost();
            }
        }
    })

    /*
    $.post("http://localhost:8000/api/blog", JSON.stringify(obj), res => {
        console.log(res);
        posts();
    }).fail( data => {
        console.log(data);
        if (data.statusText === "Forbidden") {
            sessionStorage.removeItem("id");
            newPost();
        }
    })
    */

}

const login = () => {
    const loginObj = {
        username: $("#login-username").val(),
        password: $("#login-password").val()
    }

    $.post("http://localhost:8000/api/user/login", JSON.stringify(loginObj), data => {
        console.log(data);
        sessionStorage.setItem("id", data);

        newPost();
    }).fail( (data, stat, xhm) => {
        console.log(data.responseText)
    })
}

const blogs = [{id: "0", title: "Hei", text: "Hei dette er en blog"}, {id: "1", title: "Blog 1", text: "Blog dette er inlegg 1"}, {id: "2", title: "Blog 2", text: "Blog dette er inlegg 2"}]

newPost();
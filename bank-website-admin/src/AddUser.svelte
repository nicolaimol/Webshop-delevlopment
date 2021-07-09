<script>

    import { Link } from 'svelte-routing'
    let user = {
            username: "",
            password: "",
            email: "",
            phone: "",
            address: ""
        }

    const addUser = (e) => {
        
        console.log(user)

        
        fetch("http://localhost:8080/api/v1/user",
        {
            method: 'POST',
            headers: {
                "Content-type": "application/json"
            },
            body: JSON.stringify(user)
        }).then(result => result.json().then(data => {
            console.log(data)
            e.preventDefault()
            if (data.username === null) {
                e.preventDefault()
            }
        }))
        
    }

    const klikk = (e) => {
        e.preventDefault()
    }

</script>

<div class="addUser">
    <h1>New user</h1>
    <input type="text" id="username" placeholder="Username" bind:value={user.username} >
    <input type="password" id="password" placeholder="Password" bind:value={user.password} >
    <input type="text" id="email" placeholder="Email" bind:value={user.email} >
    <input type="text" id="phone" placeholder="Phone" bind:value={user.phone} >
    <input type="text" id="address" placeholder="Address" bind:value={user.address} >

    <Link to="/users"><button type="button" on:click={addUser}>Add</button></Link>
    <Link to="/users"><button type="button" on:click={klikk}>Back</button></Link>
</div>


<style>
    .addUser {
        display: flex;
        flex-direction: column;
    }

</style>

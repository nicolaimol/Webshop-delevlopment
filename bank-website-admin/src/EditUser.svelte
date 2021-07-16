<script>

    import { Link } from 'svelte-routing'

    export let id;

    let ready = false;
    let user;

    (function() {
        console.log("ready")
        fetch(`http://localhost:8080/api/v1/user/${id}`)
        .then(response => {
            if (response.status == 200) {
                response.json().then(data => {
                    user = data;
                    console.log(user)
                    ready = true;
                })
            }
        })
    }());

    const update = () => {
        if (ready) {
            fetch(`http://localhost:8080/api/v1/user/${id}`,
            {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(user)
            })
            .then(response => {
                if (response.status == 200) {
                    response.json().then(data => {
                        console.log(data)
                    })
                }
            })
        }
    }

</script>

<div class="container">
    <h1>Edit User</h1>
    {#if !ready}
        <span>Please wait...</span>
    {:else}
        <div>
            <label for="username">Username</label>
            <input name="username" type="text" bind:value={user.username} />
            <label for="email">Email</label>
            <input name="email" type="text" bind:value={user.email} />
            <label for="phone">Phone</label>
            <input name="phone" type="text" bind:value={user.phone} />
            <label for="address">Address</label>
            <input name="address" type="text" bind:value={user.address} />

            <button on:click={update}>Update</button>
            <Link to="users"><button>Return</button></Link>
        </div>
    {/if}
</div>
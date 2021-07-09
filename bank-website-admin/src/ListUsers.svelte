<div class="listUsers">
    <h1>All users</h1>
    {#if users.length === 0}
        <p>No users</p>
    {:else}
        <table>
            <tr>
                <th>Username</th>
                <th>Email</th>
                <th>Phone</th>
                <th>Address</th>
                <th>Accounts</th>
                <th>Change</th>
                <th>Delete</th>
            </tr>
            {#each users as user}
                <tr>
                    <td>{user.username}</td>
                    <td>{user.email}</td>
                    <td>{user.phone}</td>
                    <td>{user.address}</td>
                    <td><Link to={"/accounts/"+user.id} ><button>Accounts</button></Link></td>
                    <td><button>Change</button></td>
                    <td><button on:click={deleteUser(user.id)}>Delete</button></td>
                </tr>
            {/each}
        </table>
    {/if}
    <Link to="/newuser"><button>Add new</button></Link>

</div>

<style>


</style>

<script>
    import { Link } from 'svelte-routing'

    let users = [];

    const getUsers = () => {
        fetch("http://localhost:8080/api/v1/user")
        .then(response => response.json().then(data => {
            console.log(data)
            users = data
        }))
    }

    const deleteUser = (id) => {
        fetch(`http://localhost:8080/api/v1/user/${id}`,
        {
            method: 'DELETE'
            
        })
        .then(response => response.json().then(data => {
            console.log(data)
        }))
    }

    (function() { 
        console.log("loaded")
        getUsers()
    })();

</script>
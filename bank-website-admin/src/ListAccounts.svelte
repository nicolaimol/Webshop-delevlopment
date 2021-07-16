<script>
    export let id;

    import { Link } from "svelte-routing"

    let accounts = [];


    const getAccounts = () => {
        fetch(`http://localhost:8080/api/v1/account/user/${id}`)
        .then(response => response.json().then(data => {
            console.log(data)
            accounts = data
        }))
    }

    (function() {
        getAccounts();

    }());

</script>

<div class="heipå deg">
    <h1>Accounts</h1>
    {#if accounts.length === 0}
        <h3>No accounts</h3>
    {:else}
        <table class="table">
            <tr>
                <th>Name</th>
                <th>Balance</th>
                <th>Type</th>
                <th>Transactions</th>
            </tr>
            {#each accounts as account}
                <tr>
                    <td>{account.name}</td>
                    <td>{account.balance}</td>
                    <td>{account.type}</td>
                    <td><Link to={"transaction/" + account.id}><button>Transactions</button></Link></td>
                </tr>
            {/each}
        </table>
    {/if}
    <Link to={`accounts/${id}/add`}><button>Add new account</button></Link>
    <Link to={`users`}><button>Return</button></Link>
</div>
<script>
    export let id;

    import { Link } from 'svelte-routing'

    const account = {
        name: "",
        type: "",
        owner: id
    }

    const print = () => {
        console.log(account)
    }

    const add = () => {

        fetch("http://localhost:8080/api/v1/account",
        {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(account)
        }).then(response => {
            if (response.status === 200) {
                response.json().then(data => {
                    console.log(data)
                })
            } else {
                
            }
        })


    }

</script>

<div class="container">
    <h1>Create new account</h1>
    <input type="" id="" placeholder="name" bind:value={account.name}>
    <select class="browser-default" placeholder="select type" name="type" id="type" bind:value={account.type}>
        <option value="" disabled>Select type</option>
        <option value="Debit" >Debit</option>
        <option value="Saving" >Saving</option>
    </select>
    <button type="button" on:click={add}>Add</button>
    <Link to={`/account/${id}`}><button type="button">Return</button></Link>
</div>
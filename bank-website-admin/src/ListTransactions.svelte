<script>

    export let id;

    let transactions = [];

    const getTransactions = () => {

        fetch(`http://localhost:8080/api/v1/transaction/${id}`)
        .then(response => response.json().then(data => {
            console.log(data)
            transactions = [...data.sent, ...data.rec]
        }))
    }


    (function() {
        getTransactions()
    }());
</script>

<div class="container">
    <h1>Transactions</h1>
    {#if transactions.length === 0}
        <h3>No transactions</h3>
    {:else}
        <table class="table">
            <tr>
                <th>Sender</th>
                <th>Receiver</th>
                <th>Ammount</th>
            </tr>
        {#each transactions as transaction}
            {#if transaction.sender == id}
                <tr class="dark">
                    <td>{transaction.senderU}</td>
                    <td>{transaction.receiverU}</td>
                    <td>{transaction.amount}</td>
                </tr>
            {:else}
                <tr class="light">
                    <td>{transaction.senderU}</td>
                    <td>{transaction.receiverU}</td>
                    <td>{transaction.amount}</td>
                </tr>
            {/if}
        {/each}
        </table>
    {/if}
</div>

<style>
    .dark {
        background-color: gray;
    }

    .light {
        background-color: lightgray;
    }
</style>
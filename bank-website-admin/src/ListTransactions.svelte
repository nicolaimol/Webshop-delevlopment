<script>

    export let id;

    let transactions = [];

    const getTransactions = () => {

        fetch(`http://localhost:8080/api/v1/transaction/${id}`)
        .then(response => response.json().then(data => {
            console.log(data)
        }))
    }


    (function() {
        getTransactions()
    }());
</script>

<div class="container">
    <h1>Transactions</h1>
</div>
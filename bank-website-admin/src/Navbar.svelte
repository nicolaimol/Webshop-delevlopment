<script>
    import { Link} from 'svelte-routing'
</script>


<nav>
    <div class="nav-wrapper">
        <ul class="right hide-on-med-and-down">
            <li>
                <Link to="/">Home</Link>
            </li>
            <li>
                <Link to="users">Users</Link>
            </li>
            <li>
                Accounts
            </li>
            <li>
                Transactions
            </li>
        </ul>
    </div>
</nav>
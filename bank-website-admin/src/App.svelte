<script>
	import { Router, Link, Route } from 'svelte-routing'

	import '../node_modules/materialize-css/dist/css/materialize.min.css'
	import '../node_modules/materialize-css/dist/js/materialize.min.js'


	import Navbar from './Navbar.svelte'
	import AddUser from './AddUser.svelte'
	import ListUsers from './ListUsers.svelte'
	import ListAccounts from './ListAccounts.svelte'
	import ListTransactions from './ListTransactions.svelte'
	import Login from './Login.svelte'
	import AddAccount from './AddAccount.svelte'
	import EditUser from './EditUser.svelte'

	export let url = ""

	////"start": "sirv public --no-clear"

</script>


<Router url="{url}">
	<Navbar />


	<div class="container">
		<Route path="users">
			<ListUsers />
		</Route>
		<Route path="/user/:id/edit" let:params>
			<EditUser id="{params.id}"/>
		</Route>
		<Route path="user/add">
			<AddUser />
		</Route>
		<Route path="/accounts/:id" let:params>
			<ListAccounts id="{params.id}"/>
		</Route>
		<Route path="/accounts/:id/add" let:params>
			<AddAccount id="{params.id}"/>
		</Route>
		<Route path="/transaction/:id" let:params>
			<ListTransactions id="{params.id}"/>
		</Route>
		<Route path="/">
			<Login />
		</Route>
	</div>
</Router>


<style>
	main {
		text-align: center;
		padding: 1em;
		max-width: 240px;
		margin: 0 auto;
	}

	h1 {
		color: #ff3e00;
		text-transform: uppercase;
		font-size: 4em;
		font-weight: 100;
	}

	@media (min-width: 640px) {
		main {
			max-width: none;
		}
	}
</style>
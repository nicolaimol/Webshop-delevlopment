import React, {useState} from 'react';
import {Link} from 'react-router-dom'
import { useDispatch } from 'react-redux'

import {login} from '../actions/'

const Login = () => {

    const dispatch = useDispatch();

    const loginFunc = (event) => {
        console.log(document.getElementById('loginun').value + " " + document.getElementById('loginpwd').value)
        dispatch(login('hei'));
        // use this not to redirect when not correct info
        //event.preventDefault();
    }

    const registerFunc = (event) => {

        const regObj2 = {
            username: document.getElementById('regun').value,
            password: document.getElementById('regpwd').value,
            firstname: document.getElementById('regfn').value,
            lastname: document.getElementById('regln').value,
            email: document.getElementById('regem').value
        }

        const regObj = {
            username: "nicolai",
            password: "nicolai",
            firstname: "nicolai",
            lastname: "nicolai",
            email: "nicolai"
        }

        fetch("http://localhost:8080/api/v1/users/register",
        { 
            headers: {
                'Content-Type': "application/json"
            },
            method: 'POST',
            body: JSON.stringify(regObj)

        })
        .then(res => {
            console.log(res)
            res.json().then(data => console.log(data))
            }
        )
    }

    const [showLogin, setShowLogin] = useState(true);

    const setShow = () => {
        setShowLogin(!showLogin);
    }

    const showStyle = {
        display: 'flex',
        padding: '2em'
    }

    const hideStyle = {
        display: 'none'
    }

    return (
        <div className="login">
            <div style={showLogin ? showStyle : hideStyle}>
                <input id="loginun" type="text" placeholder="username" />
                <input id="loginpwd" type="password" placeholder="password" />
                <Link to="/profile">
                    <button onClick={loginFunc}>Log in</button>
                </Link>
            </div>
            <div style={showLogin ? hideStyle : showStyle} className="register">
                <input id="regun" type="text" placeholder="username" />
                <input id="regpwd" type="password" placeholder="password" />
                <input id="regfn" placeholder="first name" />
                <input id="regln" placeholder="last name" />
                <input id="regem" placeholder="email" />
                <button onClick={registerFunc}>Register</button>
            </div>

            <p>{showLogin ? 'No user? Click here to sign in' : 'Already have a user? CLick here to log in'}</p>
            <button onClick={setShow}>{showLogin ? 'Register' : 'Login'}</button>
            

        </div>
    )
}

export default Login;
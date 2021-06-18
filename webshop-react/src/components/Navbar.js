import React from 'react';
import { Link} from 'react-router-dom'
import { useSelector } from 'react-redux'

const Navbar = () => {

    const logedIn = useSelector(state => state.login)

    return (
        <div className="navbar">
            <Link to="/">
                <p >Home</p>
            </Link>
            <Link to="/products">
                <p >Products</p>
            </Link>
            {logedIn === '' ? 
                <Link to="/login"><p >Profile</p></Link> : 
                <Link to="/profile"><p>Profile</p></Link>
            }
            
        </div>
    )
}

export default Navbar;
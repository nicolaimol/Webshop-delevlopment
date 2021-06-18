import React from 'react';

import { useSelector } from 'react-redux'

const Profile = () => {

    const select = useSelector(state => state.login)

    return (
        <div className="profile">
            <h1>Velkommen {select}</h1>
        </div>
    )
}

export default Profile;
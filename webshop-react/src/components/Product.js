import React from 'react';

import { useDispatch } from 'react-redux'
import {addCart} from '../actions'

const Product = ({name, description, url, id} ) => {

    const dispatch = useDispatch();

    return (
        <div className="product">
            <img src={url} alt={name} />
            <h2>{name}</h2>
            <h4>{description}</h4>
            <button onClick={() => dispatch(addCart({name: name}))}>Add to cart</button>
        </div>
    )

}

export default Product;
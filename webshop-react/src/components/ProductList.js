import React, {useState} from 'react';

import Product from './Product';

const ProductList = () => {

    const [products, setProducts] = useState([{ name:"hei", description:"hellu", url: "https://upload.wikimedia.org/wikipedia/commons/a/a7/React-icon.svg"}]);

    const update = () => {
        setProducts([...products, 
            { name:"hei", description:"hellu", url: "https://upload.wikimedia.org/wikipedia/commons/a/a7/React-icon.svg"}
        ]);
        console.log(products.length)
    }

    return (
        <div className="productlist">
            <div>
                {products.map((product, index) =>
                <Product key={index} name={product.name} description={product.description} url={product.url} />
            )}
            </div>
            <div>
                <button onClick={update}>update</button>
            </div>
        </div>
    )
}

export default ProductList;
import CartReducer from './cart'
import LoginReducer from './login'

import {combineReducers} from 'redux'

const allReducers = combineReducers({
    cart: CartReducer,
    login: LoginReducer
})

export default allReducers;
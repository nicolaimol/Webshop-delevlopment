export const addCart = (payload) => {
    return {
        type: 'ADD_TO_CART',
        payload: payload
    }
}

export const removeCart = (payload) => {
    return {
        type: 'REMOVE_FROM_CART',
        payload: payload
    }
}

export const login = (payload) => {
    return {
        type: 'LOGIN',
        payload: payload
    }
}

export const logout = (payload) => {
    return {
        type: 'LOGOUT'
    }
}
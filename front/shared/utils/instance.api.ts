import { FetchClient } from '../utils'

export const api = new FetchClient({
	baseUrl: '/api/proxy/api/v1',
	options: {
		credentials: 'include'
	}
})

export const apiServer = new FetchClient({
	baseUrl: process.env.BACKEND_URL as string + '/api/v1',
	options: {
		credentials: 'include'
	}
})
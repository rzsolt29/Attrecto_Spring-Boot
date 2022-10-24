import request, { Methods } from 'utils/request';

import { UserModel } from 'models/user.model';

export function getProfile() {
  return request<UserModel>({ resource: 'account/me' });
}

export function getUsers() {
  return request<UserModel[]>({ resource: 'users' });
}

export function createUser(data: UserModel) {
  return request<UserModel>({ resource: 'users', data, method: Methods.POST });
}

export function editUser(id: number, data: UserModel) {
  return request<UserModel>({ resource: `users/${id}`, data, method: Methods.PUT });
}

export function deleteUser(id: number) {
  return request({ resource: `users/${id}`, method: Methods.DELETE });
}

import React from 'react';
import { useQuery } from 'react-query';
import { Switch, Route, Redirect } from 'react-router-dom';

import { Login } from 'pages/login/login.component';

import { getProfile } from 'services/users.service';
import { routes } from 'config/routes';

export function Main() {
  const token = localStorage.getItem('token');
  const profileQuery = useQuery('profile', () => {
    if (token) {
      return getProfile();
    }
    return null;
  });

  if (profileQuery.isLoading) {
    return null;
  }

  const availableRoutes = routes.filter(
    (route) => profileQuery.data?.role && route.role.includes(profileQuery.data.role)
  );

  return (
    <div>
      <Switch>
        <Route path="/login" render={() => (!token ? <Login /> : <Redirect to="/" />)} />
        <Route
          path="/"
          render={() =>
            token && availableRoutes?.length ? (
              <Switch>
                {availableRoutes.map((route) => (
                  <Route key={route.path} path={route.path} component={route.component} />
                ))}
                <Redirect to={availableRoutes[0]?.path} />
              </Switch>
            ) : (
              <Redirect to="/login" />
            )
          }
        />
      </Switch>
    </div>
  );
}

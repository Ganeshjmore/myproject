import './home.scss';
import React from 'react';
import { Link } from 'react-router-dom';
import { Row, Col, Alert } from 'reactstrap';
import { useAppSelector } from 'app/config/store';
import ResourceSelection from "../charts/resourceSelection"

export const Home = () => {
  const account = useAppSelector(state => state.authentication.account);

  return (
    <Row>
      <Col md="3" className="pad">
        <span className="hipster rounded" />
      </Col>
      <Col md="9">
            
        {account?.login ? (
          <div>
            <Alert color="success">Welcome {account.login} !</Alert>
            <ResourceSelection></ResourceSelection>
          </div>
        ) : (
          <div>
            <Alert color="warning">
              If you want to
              <span>&nbsp;</span>
              <Link to="/login" className="alert-link">
                {' '}
                sign in
              </Link>
              , you can try the default accounts:
              <br />- Administrator (login=&quot;admin&quot; and password=&quot;admin&quot;)
              <br />- User (login=&quot;user&quot; and password=&quot;user&quot;).
            </Alert>
          </div>
        )}
      
      </Col>
    </Row>
  );
};

export default Home;
